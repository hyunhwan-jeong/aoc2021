B = []
new File("day11.in").eachLine {
    B.add(it.toCharArray().collect(i->i as String as int))
}

di = [-1, 0, 1, -1, 1, -1, 0, 1]
dj = [-1, -1, -1, 0, 0, 1, 1, 1]

ret_task1 = 0
for(step in 1..100) {
    for(i in 0..9) {
        for(j in 0..9) {
            B[i][j]++
        }
    }

    flash = new boolean[10][10]
    do {
        is_flashed = false
        for(i in 0..9) {
            for(j in 0..9) {
                if(B[i][j] > 9 && !flash[i][j]) {
                    for(k in 0..7) {
                        ni = i + di[k]
                        nj = j + dj[k]
                        if(ni<0||ni>9) continue
                        if(nj<0||nj>9) continue
                        B[ni][nj]++
                    }
                    flash[i][j] = true
                    is_flashed = true
                }
            }
        }
    } while(is_flashed);

    for(i in 0..9) {
        for(j in 0..9) {
            if(flash[i][j]) {
                B[i][j] = 0
                ret_task1++
            }
        }
    }

    println "step $step"
    for(r in B) println r
}

println ret_task1