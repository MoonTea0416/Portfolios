from sunau import AUDIO_FILE_ENCODING_ADPCM_G721
import numpy as np
import matplotlib.pyplot as plt

class Pocket:
    def __init__(self):
        data = np.loadtxt("data_Pocket.dat")
        X, self.Y = data[:,:2], np.array(data[:,2], dtype='int')
        self.N = len(data)
        # Your code goes here:
        Augment = np.ones((self.N,1))
        self.X = np.concatenate((Augment,X), axis = 1)
         
         

    def solve(self):
        w = np.zeros(3)
        X = self.X
        Y = self.Y
        T = 0
        Best_w = w
        while T< 10000:
            for j in range(self.N):
                if np.sign(np.dot(X[j], w))!= Y[j]:
                    w = np.add(w, [Y[j], Y[j]*X[j,1],Y[j]*X[j,2]])
                    break

            mistake_w_best = self.check(Best_w)
            mistake_w = self.check(w)
            if mistake_w < mistake_w_best:
                Best_w = w
            T += 1
        return Best_w

    def check(self, w):
        mistakes = 0
        for i in range(self.N):
                if np.sign(np.dot(self.X[i], w))!= self.Y[i]:
                    mistakes +=1
        return mistakes
         

    def plot(self, w, type, points_only = False):
        for i in range(self.N):
            if self.Y[i] == 1:
                plt.plot(self.X[i,1], self.X[i,2], 'ro')
            else:
                plt.plot(self.X[i,1], self.X[i,2], 'b*')
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
            plt.savefig("Pocket.png")
        else:
            print("type must be either show or save")

if __name__ == "__main__":
    obj = Pocket()
    # Run PLA and get w 
    w = obj.solve()
    # Show points and boundary 
    obj.plot(w, "show")
    # Save points and boundary 
    #obj.plot(w, "save")

