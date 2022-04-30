import numpy as np
import matplotlib.pyplot as plt
import argparse

class OVO:
    def __init__(self):
        data = np.loadtxt("data.dat")
        X, Y = data[:,:2], np.array(data[:,2], dtype='int')

        self.N = len(data)
        self.X = np.concatenate((np.ones((self.N,1)), X), axis=1)
        self.Y = Y
        self.data = np.concatenate((self.X, np.reshape(self.Y,(self.N,1))), axis=1)

    def PLA(self, X, Y):
        # Return weight of the boundary line which separates 
        # two groups in the given data X and Y.  
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
        # Train a model using One-Versus-One method.
        # Call PLA on every pair of groups. 
        # The purpose of this function is to calculate W ang Groups. 

        type = list(set(self.Y))
        pairs = [(type[i],type[j]) for i in range(len(type)) for j in range(i+1, len(type))]
        self.W = np.zeros((len(pairs), 3)) # Each row of W is one boundary line between two groups. 
        self.Groups = np.zeros((len(pairs),2), dtype=int) # Each row of Groups is the types of two groups, where the first number is the type of the positive side and the second number is the type of the negative side. Type is an integer from 0 to 3. 
        counter = 0
        for pair in pairs:
            data = self.data[(self.data[:, 3] == pair[0])|(self.data[:, 3] == pair[1])]
            X = data[:,:3]
            Y = data[:,-1]
            Y[Y==pair[0]] = 1
            Y[Y==pair[1]] = -1
            w = self.PLA(X,Y)
            self.W[counter] = w
            self.Groups[counter] = pair
            counter += 1
 
    def find_group(self, point):
        # Given a point (x,y), use W and Groups to return the group type of this point, which is an integer from 0 to 3. 
        point.insert(0,1)
        waiting_list = np.zeros(4)
        for i in range (0, self.Groups.shape[0]):
            w = self.W[i]
            if np.sign(np.dot(point, w)) == 1:
                waiting_list[self.Groups[i][0]]+=1
            elif np.sign(np.dot(point, w)) == -1:
                waiting_list[self.Groups[i][1]]+=1
                 
        return waiting_list.argmax()
         
        

    def plot(self, type):
        M = 100
        grid = np.zeros((M**2, 3))
        p = np.linspace(-1,1,M)
        k=0
        for i in range(M):
            for j in range(M):
                grid[k] = [p[i], p[j], self.find_group([p[i],p[j]])]
                k+=1
        plt.figure(figsize=(8, 8), dpi=80)
        style = ['rs', 'bs', 'ks', 'ys']
        for i in range(4): 
            data = grid[grid[:,2]==i]
            plt.plot(data[:,0], data[:,1], style[i])
        plt.xticks([], [])
        plt.yticks([], [])
        plt.xlim(-1,1)
        plt.ylim(-1,1)
        
        if type == "show":
            plt.show()
        elif type == "save":
            plt.savefig("result.png")
        else:
            print("type must be either show or save")

if __name__ == "__main__":
    obj = OVO()
    # Run 
    obj.train()
    # # Show results
    obj.plot("show")
    # Save results
    # obj.plot("save")

