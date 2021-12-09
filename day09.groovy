B = []

new File("day09.in").eachLine {
    B.add(it.toCharArray().collect(i->i as int - 48))
}

N = B.size()
M = B[0].size()

di = [0, 0, -1, 1]
dj = [-1, 1, 0, 0]

ret = 0
for(i in 0..N-1) {
    for(j in 0..M-1) {
        islow = true
        for(k in 0..3) {
            ni = i + di[k]
            nj = j + dj[k]

            if(ni < 0 || ni >= N) continue
            if(nj < 0 || nj >= M) continue

            if(B[ni][nj] <= B[i][j]) {
                islow = false
                break
            }
        }
        if(islow) {
            ret += B[i][j] + 1
        }
    }
}

println ret
