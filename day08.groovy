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

ret = 0

new File("day08.in").eachLine { line ->
    d = []

    for(i in 0..9) {
        d[i] = [] as Set
    }
    
    (l, r) = line.split(" \\| ")
    l = l.split(" ").collect{ it -> 
        [it.toCharArray()].flatten() as Set
    }
    for(x in l) {
        if(x.size() == 3) d[7] = x
        else if(x.size() == 4) d[4] = x
        else if(x.size() == 7) d[8] = x
        else if(x.size() == 2) d[1] = x
    }

    for(x in l) {
        if(x.size() == 5) {
            if(d[1].intersect(x).size() == 2) {
                d[3] = x
            } else if(d[4].intersect(x).size() == 3) {
                d[5] = x
            } else {
                d[2] = x
            }
        } 
    }
    
    for(x in l) {
        if(x.size() == 6) {
            if(d[4].intersect(x).size() == 4) {
                d[9] = x
            } else if(d[5].intersect(x).size() == 4) {
                d[0] = x
            } else {
                d[6] = x
            }
        }
    }
    // println d
    r = r.split(" ").collect{ it -> 
        [it.toCharArray()].flatten() as Set
    }

    num = 0 
    
    for(x in r) {
        //print x
        num *= 10
        for(i in 0..9) {
            if(x.intersect(d[i]).size() == d[i].size() && x.intersect(d[i]).size() == x.size()) {
                /*print x
                print x.intersect(d[i])
                print " "
                print i*/
                num += i
                break
            }
        }
        //println ""
        
    }
    println num   
    ret += num
}

println ret