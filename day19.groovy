
scanner = [[]]

new File("day19_sample.in").eachLine{ line ->
    if(line.startsWith("---")) {
    }
    else if(line == "") scanner.add([])
    else {
        (x,y,z) = line.split(",").collect(i->i as int)
        scanner[scanner.size()-1].add([x,y,z])
    }
}

def roll(S) {
    S.collect(v -> [v[0], v[2], -v[1]])
}

def turn(S) {
    S.collect(v -> [-v[1], v[0], v[2]])
}

def rotations(S) {
    ret = [S]
    for(cycle in 1..2) {
        for(step in 1..3) {
            S = roll(S)
            ret.add(S)
            for(i in 1..3) {
                S = turn(S)
                ret.add(S)
            }
        }
        S = roll(turn(roll(S)))
        ret.add(S)
    }
    ret
}


R = scanner.collect{
    rotations(it)
}

