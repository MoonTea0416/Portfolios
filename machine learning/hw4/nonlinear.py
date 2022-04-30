import numpy as np
import matplotlib.pyplot as plt
import argparse

class Nonlinear:
    def __init__(self, data):
        data = np.loadtxt("data"+data+".dat")
        X, self.Y = data[:,:2], np.array(data[:,2], dtype='int')
        self.N = len(data)
        new_column_1 = np.reshape(X[:,0]**2,(self.N,1))
        new_column_2 = np.reshape(X[:,1]**2,(self.N,1))
        X = np.concatenate((new_column_1, X), axis=1)
        X = np.concatenate((new_column_2, X), axis=1)
        X = np.concatenate((np.ones((self.N,1)), X), axis=1)
          
        self.X = X
         
        
    def PLA(self, X, Y):
        # Return weight of the boundary line which separates 
        # two groups in the given data X and Y. 
        self.w = np.zeros(5)
        while True:
            counter = 0
            for i in range(self.N):
                if np.sign(np.dot(X[i], self.w))!= Y[i]:
                    self.w = np.add(self.w, [Y[i], Y[i]*X[i,1],Y[i]*X[i,2],Y[i]*X[i,3],Y[i]*X[i,4]])
                    continue
                else:
                    counter += 1
            if counter == self.N:
                 break


    def train(self):
        # Call PLA on transformed data to return the weights of the boundary.
        X = self.X
        Y = self.Y
        return self.PLA(X, Y)

    def transform(self, point):
        # Define the transformation on a given point. 
        return(1,point[1]**2,point[0]**2,point[0],point[1])

    def find_group(self, point):
        # Return the group (+1 or -1) on a given point.
        return np.sign(np.dot(self.transform(point), self.w))
    

    def plot(self, type):
        M = 100
        grid = np.zeros((M**2, 3))
        p = np.linspace(-1,1,M)
        k=0
        for i in range(M):
            for j in range(M):
                grid[k] = [p[i], p[j], self.find_group([p[i],p[j]])]
                k+=1

        data = grid[grid[:,2]==1]
        plt.plot(data[:,0], data[:,1], 'b.')
        data = grid[grid[:,2]==-1]
        plt.plot(data[:,0], data[:,1], 'r.')

        plt.xticks([], [])
        plt.yticks([], [])
        plt.xlim(-1,1)
        plt.ylim(-1,1)
        plt.gca().set_aspect('equal', adjustable='box')
        
        if type == "show":
            plt.show()
        elif type == "save":
            plt.savefig("result.png")
        else:
            print("type must be either show or save")

if __name__ == "__main__":

    parser = argparse.ArgumentParser(description='Nonlinear')
    parser.add_argument('-data', dest='data', required = True, type = str, help='Name of Dataset')
    args = parser.parse_args()

    obj = Nonlinear(args.data)
    # Run 
    obj.train()
    # Show results
    obj.plot("show")
    # Save results
    # obj.plot("save")

