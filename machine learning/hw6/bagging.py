from matplotlib.cbook import index_of
import numpy as np
import matplotlib.pyplot as plt
import argparse

class Bagging:
    def __init__(self, T):
        np.random.seed(2) # Do not change the seed. 
        self.data = np.loadtxt("data_Bagging.dat")
        X, Y = self.data[:,:2], np.array(self.data[:,2], dtype='int')
        self.N = X.shape[0]
        self.T = T 
        self.X = np.concatenate((np.ones((self.N,1)), X), axis=1)
        self.Y = Y
        self.W = np.zeros((self.T, self.X.shape[1]))

    def pocket(self, X, Y):
        w = np.zeros(3)
        T = 0
        Best_w = w
        while T< 1000:
            for j in range(self.N//4):
                if np.sign(np.dot(X[j], w))!= Y[j]:
                    w = np.add(w, [Y[j], Y[j]*X[j,1],Y[j]*X[j,2]])
                    break

            mistake_w_best = self.check(Best_w, X, Y)
            mistake_w = self.check(w, X, Y)
            if mistake_w < mistake_w_best:
                Best_w = w
            T += 1
        return Best_w

    def check(self, w, X, Y):
        mistakes = 0
        for i in range(self.N//4):
                if np.sign(np.dot( X[i], w))!= Y[i]:
                    mistakes +=1
        return mistakes

    def train(self):
        data = self.data
        T = self.T
        N = self.N
        for t in range(0,T):
            sample_index = np.random.choice(N-1, N//4, replace=True)
            sample = data[sample_index]
            X, Y = sample[:,:2], np.array(sample[:,2], dtype='int')
            X = np.concatenate((np.ones((N//4,1)), X), axis=1)
            w = self.pocket(X,Y)
            self.W[t] = w

    def prediction(self, point):
        red = 0
        blue = 0
        point.insert(0,1)
        for w in self.W:
            if (np.sign(np.dot(point, w))) == 1:
                red += 1
            elif (np.sign(np.dot(point, w))) == -1:
                blue += 1

        if red > blue:
            return 1
        else:
            return -1
        
    def plot(self, points_only = False):
        for i in range(self.N):
            if self.Y[i] == 1:
                plt.plot(self.X[i,1], self.X[i,2], 'ro')
            else:
                plt.plot(self.X[i,1], self.X[i,2], 'b*')
        if not points_only:
            for w in self.W:
                x = np.arange(0,1.01,0.1)
                y = -(w[0] + w[1] * x)/w[2]
                plt.plot(x,y, alpha=0.3,color='gray')

            M = 100
            boundary = []
            p = np.linspace(0,1,M)
            for i in range(M):
                k = 0
                pre_color = None
                for j in range(M):
                    cur_color = self.prediction([p[i],p[j]])
                    if k == 0:
                        pre_color = cur_color 
                    elif pre_color != cur_color: 
                        pre_color = cur_color 
                        # print(cur_color)
                        boundary.append([p[i], p[j], cur_color])
                    k+=1
            boundary = np.array(boundary)
            data = boundary[boundary[:,2]==-1]
            plt.plot(data[:,0], data[:,1], 'k-')

        plt.title("Bagging")
        plt.xticks([], [])
        plt.yticks([], [])
        plt.xlim(0,1)
        plt.ylim(0,1)

        plt.savefig("result_bagging"+".png")
        plt.show()

if __name__ == "__main__":
    # Run the code using this line in the terminal: python bagging .py -T 25

    parser = argparse.ArgumentParser(description='Bagging')
    parser.add_argument('-T', dest='T', required = True, type = int, help='Number of lines')
    args = parser.parse_args()

    obj = Bagging(args.T)
    obj.train()
    print()
    # Show points and boundary 
    obj.plot()