freq = []
for(i in 0..11) {
    freq.add(0)
}

n = 0
new File("day03.in").readLines().forEach { line ->
    n++
    for(i in 0..line.length()-1) {
        if(line[i]=="1") freq[i]++
    }
}

println freq
println n

gamma = 0
epsilon = 0

for(i in 0..11) {
    gamma *= 2
    epsilon *= 2
    if(freq[i]>=n/2) {
        gamma += 1
    } else {
        epsilon += 1
    }


}

println gamma
println epsilon
println gamma*epsilon


mat = []

new File("day03.in").readLines().forEach { line ->
    mat.add(line)
}

remains = []
for(i in 0..mat.size()-1) {
    remains.add(true)
}

n = mat.size()
m = mat[0].length()

oxygen = 0
total = n
for(j in 0..m-1) {
    oxygen *= 2
    cnt = 0

    if(total == 0) continue
    if(total == 1) {
        for(i in 0..n-1) {
            if(remains[i] && mat[i][j]=="1") {
                oxygen++
                break
            }
        }
        continue
    }

    for(i in 0..n-1) {
        if(remains[i] && mat[i][j]=="1") cnt++
    }

    if(cnt>=total/2.0) {
        for(i in 0..n-1) {
            if(remains[i] && mat[i][j]=="0") {
                remains[i] = false
                total--
            }
        }
        oxygen++
    } else {
        for(i in 0..n-1) {
            if(remains[i] && mat[i][j]=="1") {
                remains[i] = false
                total--
            }
        }
    }
}

remains = []
for(i in 0..n-1) {
    remains.add(true)
}

co2 = 0
total = n
for(j in 0..m-1) {
    co2 *= 2

    if(total == 0) continue
    if(total == 1) {
        for(i in 0..n-1) {
            if(remains[i] && mat[i][j]=="1") {
                co2++
                break
            }
        }
        continue
    }

    cnt = 0
    for(i in 0..n-1) {
        if(remains[i] && mat[i][j]=="1") cnt++
    }

    if(cnt>=total/2.0) {
        for(i in 0..n-1) {
            if(remains[i] && mat[i][j]=="1") {
                remains[i] = false
                total--
            }
        }
    } else {
        for(i in 0..n-1) {
            if(remains[i] && mat[i][j]=="0") {
                remains[i] = false
                total--
            }
        }
        co2++
    }
}

println "oxygen $oxygen"
println "co2 $co2"
println "answer ${oxygen*co2}"