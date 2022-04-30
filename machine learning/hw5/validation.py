import numpy as np
import matplotlib.pyplot as plt
from sklearn import linear_model
import re
import argparse

from sympy import N

class Validation:
    def __init__(self, dataset_index):
        data = np.loadtxt("data"+dataset_index+".dat")
        self.dataset_index = int(dataset_index)
        # X and Y are all one dimensional vectors. 
        self.X, self.Y = data[:,0], data[:,1]
        self.best_model = '' # model fit on X and Y 
        self.best_regularizer = '' # either Ridge or Lasso
        self.best_lambda = 0 # one of 0.01, 0.05, 0.1
        self.best_order = 0 # either 2 or 10

    def transform(self, x, order):
        # Input: A one dimensional vector x with shape (N,) or a single real number x 
        # Return: A two dimensional matrix with shape (N, order+1) and columns as:
        #         1, x, x^2, ..., x^order
        result = np.ones((x.shape[0],1))
        for i in range (1,order+1):
            new_column = np.reshape(x**i,(x.shape[0],1))
            result = np.concatenate((new_column, result), axis=1)
        return result

    def train(self):
        # Train a model from X and Y using 10-fold cross-validation over hypothesis sets, regularizers, and lambda values. 
        # Update best_model, best_regularizer, best_lambda, and best_order. 
        N = len(self.X)
        orders = [2,10]
        regularizers = [linear_model.Lasso, linear_model.Ridge]
        lambdas = [0.01, 0.05, 0.1]
        least_error = float('inf')
        for order in orders:
            x = self.transform(self.X,order)
            for regularizer in regularizers:
                for lambda_value in lambdas:
                    errors = []
                    for fold in range (0,10):
                        testing_index = [int((N/10)*fold),int((N/10)*(fold+1))]
                        if testing_index[1] < N:
                            X_testing = x[testing_index[0]:testing_index[1],:]
                            Y_testing = self.Y[testing_index[0]:testing_index[1]]
                            X_training = np.delete(x, slice(testing_index[0],testing_index[1]),0)
                            Y_training = np.delete(self.Y, slice(testing_index[0],testing_index[1]))
                        else:
                            X_testing = x[testing_index[0]:,:]
                            Y_testing = self.Y[testing_index[0]:]
                            X_training = x[:testing_index[0], :]
                            Y_training = self.Y[:testing_index[0]]
                        #training model
                        clf = regularizer(alpha=lambda_value) #define the regularizer
                        reg = clf.fit(X_training, Y_training) #reg is model
                        #test model
                        Y_predict = reg.predict(X_testing) 
                        ssd = np.sum((Y_testing-Y_predict)**2)
                        errors.append(ssd)
                    #compare model
                    current_error = np.mean(errors)
                    if current_error < least_error:
                        least_error = current_error
                        self.best_model = reg
                        self.best_regularizer = regularizer
                        self.best_lambda = lambda_value
                        self.best_order = order


 
    def prediction(self, points):
        # Input: A one dimensional vector of x values.
        # Return: A one dimensional vector of predicted y values based on the best model. 
        x = self.transform(points,self.best_order)
        return self.best_model.predict(x)

    def plot(self):
        plt.figure(figsize=(8, 6), dpi=80)
        X_value = np.linspace(-1,1,100)
        Y_pred = self.prediction(X_value)
        
        targets = [lambda x: x**10 - 2*x**8 + 3*x**7 + 4*x**3 + 2*x**2 + x + 1, 
                   lambda x: -2*x**3 + 1]
        Y_target = targets[self.dataset_index-1](X_value)

        plt.plot(X_value,Y_pred, 'b-', label="prediction")
        plt.plot(X_value,Y_target, 'r-', label="target")
        plt.plot(self.X, self.Y, 'k.')
        plt.legend(loc='lower center')
        plt.title("The dataset is data" + str(self.dataset_index) + ".\n" +
                  "The hypothesis set is polynomials at order " + str(self.best_order) + ".\n" + 
                  "The regularizer and lambda value are " + re.findall("\.[a-zA-Z]+'>", str(self.best_regularizer))[0][1:-2] + 
                  " and " + str(self.best_lambda) + ".")
        
        plt.savefig("result"+str(self.dataset_index)+".png")
        plt.show()

if __name__ == "__main__":

    parser = argparse.ArgumentParser(description='Validation')
    parser.add_argument('-data', dest='data', required = True, type = str, help='Name of Dataset')
    args = parser.parse_args()

    obj = Validation(args.data)
    # Run 
    obj.train()
    # Show and save results
    obj.plot()

