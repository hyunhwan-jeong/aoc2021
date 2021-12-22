
scanner = [[]]
rmat = []

new File("day19_sample.in").eachLine{ line ->
    if(line.startsWith("---")) {
    }
    else if(line == "") scanner.add([])
    else {
        (x,y,z) = line.split(",").collect(i->i as int)
        scanner[scanner.size()-1].add([x,y,z])
    }
}

def calc_det(mat) {
    def ret = 0
    for(j in 0..2) {
        def mul = 1
        for(i in 0..2) {
            mul *= mat[i][(i+j)%3]
        }
        ret += mul
    }


    for(j in 0..2) {
        def mul = 1
        k = 0 
        for(i in 2..0) {
            mul *= mat[i][(j+k)%3]
            k++
        }
        ret -= mul
    }

    return ret
}

def gen_rotation_mat(a, b, c) {
    for(i in [1,-1]) {
        for(j in [1,-1]) {
            for(k in [1,-1]) {
                def mat = new int[3][3]
                mat[0][a] = i
                mat[1][b] = j
                mat[2][c] = k
                det = calc_det(mat)
                if(det == 1) rmat.add(mat)
            }
        }
    }
       
}

gen_rotation_mat(0, 1, 2)
gen_rotation_mat(0, 2, 1)
gen_rotation_mat(1, 0, 2)
gen_rotation_mat(1, 2, 0)
gen_rotation_mat(2, 0, 1)
gen_rotation_mat(2, 1, 0)

def rotate(S) {
    def ret = []
    for(mat in rmat) {
        def new_S = S.collect { it->
            def x = it[0]
            def y = it[1]
            def z = it[2]
            def x2 = mat[0][0]*x + mat[0][1]*y + mat[0][2]*z
            def y2 = mat[1][0]*x + mat[1][1]*y + mat[1][2]*z
            def z2 = mat[2][0]*x + mat[2][1]*y + mat[2][2]*z
            [x2,y2,z2]
        }
        ret.add(new_S)
        
    }
    ret
}

def find_candidates(R0, R1, axis) {
    def ret = []
    def S0 = R0.collect{it[axis]} as HashSet
    def S1 = R1.collect{it[axis]} as HashSet
    
    for(pos in -3000..3000) {
        def cnt = 0
        S1.each{
            where = pos + it
            if(S0.contains(where)) ++cnt
        }
        if(cnt>=12) ret.add(pos)
    }
    ret
}

println rmat[0]
println rmat[2]
scanner_with_rotations = scanner.collect { rotate(it) }
n = scanner_with_rotations.size()

beacons = [] as Set

def match(va, vb, vc, R0, R1) {
    def S0 = R0 as HashSet
    for(a in va) for(b in vb) for(c in vc) {
        def S1 = R1.collect{[it[0]+a,it[1]+b,it[2]+c]} as HashSet
        if(S0.intersect(S1).size() < 12) continue 
        //println "$a,$b,$c"
        //return R1.collect { [it[0]+a,it[1]+b,it[2]+c] }
        return [a,b,c]
    }
    return null
}

q = [] as Queue

q.add([0, scanner_with_rotations[0][0], 0, 0, 0])
visited = new boolean[n]
while(!q.isEmpty()) {
    (i, B, x, y, z) = q.poll()
    if(visited[i]) continue
    println "$i: $x $y $z"
    visited[i] = true
    B.each{
        beacons.add([it[0]+x, it[1]+y, it[2]+z])
    }
    for(j in 0..n-1) if(!visited[j]) {
        for(k in 0..23) {
            def ret0 = find_candidates(B, scanner_with_rotations[j][k], 0)
            if(ret0.isEmpty()) continue
            def ret1 = find_candidates(B, scanner_with_rotations[j][k], 1)
            if(ret1.isEmpty()) continue
            def ret2 = find_candidates(B, scanner_with_rotations[j][k], 2)
            if(ret2.isEmpty()) continue
            def match_ret = match(ret0, ret1, ret2, B, scanner_with_rotations[j][k])
            if(match_ret != null) {
                println "match at $i and $j"
                q.add([j, scanner_with_rotations[j][k], match_ret[0]+x, match_ret[1]+y, match_ret[2]+z])
                break
            }
        }
    }
}

println visited
println beacons
println beacons.size()