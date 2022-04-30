import numpy as np
import matplotlib.pyplot as plt
import argparse

class Nonlinear:
    def __init__(self):
        data = np.loadtxt("data_nonlinear.dat")
        self.original_X, self.Y = data[:,:2], np.array(data[:,2], dtype='int')
        self.N = len(data)

        # Call method transform on original_X and save the transformed X as self.X:
        X = self.original_X
        new_column_1 = np.reshape(X[:,1]**2,(self.N,1))
        new_column_2 = np.reshape(X[:,0]**2,(self.N,1))
        new_column_3 = np.reshape(X[:,1]**3,(self.N,1))
        new_column_4 = np.reshape(X[:,0]**3,(self.N,1))
        X = np.concatenate((new_column_1, X), axis=1)
        X = np.concatenate((new_column_2, X), axis=1)
        X = np.concatenate((new_column_3, X), axis=1)
        X = np.concatenate((new_column_4, X), axis=1)
        X = np.concatenate((np.ones((self.N,1)), X), axis=1)
        self.w = np.zeros(7)
        self.X = X
        
        
    def PLA(self, X, Y):
        # Return weight of the boundary line which separates 
        # two groups in the given data X and Y. 
        while True:
            counter = 0
            for i in range(self.N):
                if np.sign(np.dot(X[i], self.w))!= Y[i]:
                    self.w = np.add(self.w, [Y[i], Y[i]*X[i,1],Y[i]*X[i,2],Y[i]*X[i,3],Y[i]*X[i,4],Y[i]*X[i,5],Y[i]*X[i,6]])
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
        # Define the transformation on a single given point. 
        return(1,point[0]**3,point[1]**3,point[0]**2,point[1]**2,point[0],point[1])

    def prediction(self, point):
        # Return the color/group (+1 or -1) on a given point.
        return np.sign(np.dot(self.transform(point), self.w))

    def plot(self):
        # Draw points       
        data = self.original_X[self.Y==1]
        plt.plot(data[:,0], data[:,1], 'r.')
        data = self.original_X[self.Y==-1]
        plt.plot(data[:,0], data[:,1], 'b.')
        # Draw boundary 
        M = 100
        p = np.linspace(-1,1,M)
        boundary = []
        for i in range(M): # horizontal line 
            k = 0
            pre_color = None
            for j in range(M):
                cur_color = self.prediction([p[i],p[j]])
                if k == 0:
                    pre_color = cur_color 
                elif pre_color != cur_color: 
                    pre_color = cur_color 
                    boundary.append([p[i], p[j], cur_color])
                k+=1
        for j in range(M): # vertical line
            k = 0
            pre_color = None
            for i in range(M):
                cur_color = self.prediction([p[i],p[j]])
                if k == 0:
                    pre_color = cur_color 
                elif pre_color != cur_color: 
                    pre_color = cur_color 
                    boundary.append([p[i], p[j], cur_color])
                k+=1
        boundary = np.array(boundary)
        plt.plot(boundary[:,0], boundary[:,1], 'k.', markersize=2)

        plt.xticks([], [])
        plt.yticks([], [])
        plt.xlim(-1,1)
        plt.ylim(-1,1)
        plt.gca().set_aspect('equal', adjustable='box')
        
        plt.savefig("result_nonlinear.png")
        #plt.savefig("points_nonlinear.png")
        plt.show()

if __name__ == "__main__":

    obj = Nonlinear()
    # Run 
    obj.train()
    # Show and save result
    obj.plot()

