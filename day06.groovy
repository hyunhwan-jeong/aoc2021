vec = new File("day06.in").readLines().get(0).split(",").collect(it->it as int)

for(i in 1..80) {
    nadds = 0
    for(j in 0..vec.size()-1) {
        if(vec[j] == 0) {
            vec[j] = 6
            nadds++
        } else {
            vec[j]--
        }
    }

    if(nadds > 0) {
        for(j in 1..nadds) {
            vec.add(8)
        }
    }

}

println vec.size()


// solution for part 2
vec = new File("day06.in").readLines().get(0).split(",").collect(it->it as int)

freq = new long[9]

for(v in vec) freq[v]++

for(i in 1..256) {
    next = new long[9]

    for(j in 0..7) {
        next[j] = freq[j+1]
    }
    next[8] = freq[0]
    next[6] += freq[0]

    for(j in 0..8) freq[j] = next[j]
}

// println freq.sum()
// println 26984457539 == freq.sum()

println freq.sum()