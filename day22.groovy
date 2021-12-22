ON = []
X1 = []
X2 = []
Y1 = []
Y2 = []
Z1 = []
Z2 = []

new File("day22_sample.in").eachLine { it ->
    (on, x1, x2, y1, y2, z1, z2) = it.split(" ").collect{i->i as int}
    ON.add(on)
    X1.add(x1)
    X2.add(x2)
    Y1.add(y1)
    Y2.add(y2)
    Z1.add(z1)
    Z2.add(z2)
}

S = new int[200][200][200]
N = ON.size()

for(i in 0..N-1) {
    if(X1[i] > 50) continue
    if(X2[i] < -50) continue
    if(Y1[i] > 50) continue
    if(Y2[i] < -50) continue
    if(Z1[i] > 50) continue
    if(Z2[i] < -50) continue

    for(xx in Math.max(-50,X1[i])..Math.min(50,X2[i])) 
    for(yy in Math.max(-50,Y1[i])..Math.min(50,Y2[i]))  
    for(zz in Math.max(-50,Z1[i])..Math.min(50,Z2[i])) { 
        
        S[xx+50][yy+50][zz+50] = ON[i]
    }
}
cnt = 0
for(i in 0..199) for(j in 0..199) for(k in 0..199) cnt += S[i][j][k]
println cnt

/*
L = ON.size()

SX = X1 as Set + X2 as Set 
SY = Y1 as Set + Y2 as Set
SZ = Z1 as Set + Z2 as Set
SX = SX as List
SY = SY as List
SZ = SZ as List
SX.sort()
SY.sort()
SZ.sort()

DX = [:]
DY = [:]
DZ = [:]

DX[0] = 1

i = 0
SX.each { x -> DX[x] = i++ }
i = 0
SY.each { y -> DX[y] = i++ }
i = 0
SZ.each { z -> DX[z] = i++ }

N = SX.size()
M = SY.size()
P = SZ.size()

println SX


C = new int[N+1][M+1][P+1]

for(i in 0..L-1) {
    x1 = DX[X1[i]]
    x2 = DX[X2[i]]
    y1 = DX[Y1[i]]
    y2 = DX[Y2[i]]
    z1 = DX[Z1[i]]
    z2 = DX[Z2[i]]
    for(x in x1..x2-1) {
        for(y in y1..y2-1) {
            for(z in z1..z2-1) {
                if(ON[i]==0) {
                    C[x][y][z] = 0
                } else {
                    C[x][y][z] = 1
                }
            }
        }
    }
}

ret = 0

for(i in 0..N-1) {
    for(j in 0..M-1) {
        for(k in 0..P-1) {
            if(C[i][j][k]==1) {
                ret += (SX[i+1]-SX[i]) * (SY[j+1]-SY[j]) * (SZ[k+1]-SZ[k])
            }
        }
    }
}

println ret*/