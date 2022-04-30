import numpy as np
import matplotlib.pyplot as plt

class PLA:
    def __init__(self):
        data = np.loadtxt("data.dat")
        self.X, self.Y = data[:,:2], np.array(data[:,2], dtype='int')
        self.N = self.X.shape[0]


    def solve(self):
        w = np.zeros(3)
        X = self.X
        Y = self.Y
        while True:
            counter = 0
            for i in range(self.N):
                if np.sign(w[0]+w[1]*X[i,0]+w[2]*X[i,1])!= Y[i]:
                    w = np.add(w, [Y[i], Y[i]*X[i,0],Y[i]*X[i,1]])
                    continue
                else:
                    counter += 1
            if counter == self.N:
                 return w

     
    def plot(self, w, type, points_only = False):
        for i in range(self.N):
            if self.Y[i] == 1:
                plt.plot(self.X[i,0], self.X[i,1], 'ro')
            else:
                plt.plot(self.X[i,0], self.X[i,1], 'b*')
        if not points_only:
            x = np.arange(0,1.01,0.1)
            y = -(w[0] + w[1] * x)/w[2]
            plt.plot(x,y,'k')
        plt.xticks([], [])
        plt.yticks([], [])
        plt.xlim(0,1)
        plt.ylim(0,1)
        if type == "show":
            plt.show()
        elif type == "save":
            plt.savefig("PLA.png")
        else:
            print("type must be either show or save")

if __name__ == "__main__":
    obj = PLA()
    # Run PLA and get w 
    w = obj.solve()
    # Show points and boundary 
    obj.plot(w, "show")
    # Save points and boundary 
    obj.plot(w, "save")

