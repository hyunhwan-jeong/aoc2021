B = []

new File("day09.in").eachLine {
    B.add(it.toCharArray().collect(i->i as int - 48))
}

N = B.size()
M = B[0].size()

di = [0, 0, -1, 1]
dj = [-1, 1, 0, 0]

ret_task1 = 0
ret_task2 = []
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
            ret_task1 += B[i][j] + 1
            ret_task2.add(bfs(i, j))
        }
    }
}

ret_task2.sort()

def bfs(si, sj) {
    ret = 0
    visited = new boolean[N][M]

    q = [] as Queue
    q << [si, sj]
    while(!q.isEmpty()) {
        (i, j) = q.poll()
        if(visited[i][j]) continue
        visited[i][j] = true
        ret++
        for(k in 0..3) {
            ni = i + di[k]
            nj = j + dj[k]

            if(ni < 0 || ni >= N) continue
            if(nj < 0 || nj >= M) continue
            if(B[ni][nj] == 9) continue

            // if(B[ni][nj] == 1 + B[i][j]) {
            if(B[ni][nj] > B[i][j]) {
                q << [ni, nj]
            }
        }
    }
    return ret
}
println ret_task1
println ret_task2[-3] * ret_task2[-2] * ret_task2[-1]