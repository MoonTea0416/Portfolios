import numpy as np
import matplotlib.pyplot as plt
import argparse

class Blending:
    def __init__(self, T):
        self.data = np.loadtxt("data_blending.dat")
        X, Y = self.data[:,:2], np.array(self.data[:,2], dtype='int')
        self.T = T
        self.N = len(self.data)
        self.X = np.concatenate((np.ones((self.N,1)), X), axis=1)
        self.Y = Y

    def PLA(self, X, Y):        
        w = np.zeros(3)
        while True:
            counter = 0
            for j in range(X.shape[0]):
                if np.sign(np.dot(X[j], w))!= Y[j]:
                    w = np.add(w, [Y[j], Y[j]*X[j,1],Y[j]*X[j,2]])
                    continue
                else:
                    counter += 1
            if counter == X.shape[0]:
                 return w

    def train(self):
        # W is T rows of lines and W_mean is the average all lines. 
        # Add your code here:
        data = self.data
        T = self.T
        X = self.X
        W = np.zeros([T,3])
        for t in range(0, T):
            np.random.shuffle(data)
            X, Y = data[:,:2], np.array(data[:,2], dtype='int')
            X = np.concatenate((np.ones((self.N,1)), X), axis=1)

            w = self.PLA(X,Y)
            W[t] = w

        W_mean = np.mean(W, axis=0)

        return W, W_mean
        
    def plot(self, W, W_mean, points_only = False):
        for i in range(self.N):
            if self.Y[i] == 1:
                plt.plot(self.X[i,1], self.X[i,2], 'ro')
            else:
                plt.plot(self.X[i,1], self.X[i,2], 'b*')
        if not points_only:
            for w in W:
                x = np.arange(0,1.01,0.1)
                y = -(w[0] + w[1] * x)/w[2]
                plt.plot(x,y, alpha=0.3,color='gray')
   
            x = np.arange(0,1.01,0.1)
            y = -(W_mean[0] + W_mean[1] * x)/W_mean[2]
            plt.plot(x,y, alpha=1,linewidth = 2, color='k')

        plt.xticks([], [])
        plt.yticks([], [])
        plt.xlim(0,1)
        plt.ylim(0,1)
        plt.title("Blending")
        plt.savefig("result_blending"+".png")
        plt.show()

if __name__ == "__main__":
    # Run the code using this line in the terminal: python blending.py -T 20
    
    parser = argparse.ArgumentParser(description='Blending')
    parser.add_argument('-T', dest='T', required = True, type = int, help='Number of lines')
    args = parser.parse_args()

    obj = Blending(args.T)
    W, W_mean= obj.train()
    # Show points and boundary 
    obj.plot(W, W_mean)