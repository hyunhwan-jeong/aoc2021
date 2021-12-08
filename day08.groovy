ret = 0

new File("day08.in").eachLine { line ->
    d = new String[10]

    (l, r) = line.split(" \\| ")
    l = l.split(" ").collect{ it -> 
        [it.toCharArray()].flatten().sort().join()
    }
    for(x in l) {
        if(x.length() == 3) d[7] = x
        if(x.length() == 4) d[4] = x
        if(x.length() == 7) d[8] = x
        if(x.length() == 2) d[1] = x
    }
    r = r.split(" ").collect{ it -> 
        [it.toCharArray()].flatten().sort().join()
    }

    for(x in r) {
        if(x==d[1]) {
            ++ret
        }
        else if(x==d[4]) {
            ++ret
        } 
        else if(x==d[7]) {
            ++ret
        }
        else if(x==d[8]) {
            ++ret
        }
    }
}

println ret