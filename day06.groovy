vec = new File("day06_sample.in").readLines().get(0).split(",").collect(it->it as int)

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

