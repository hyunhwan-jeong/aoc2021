cmd = []
new File("day24.in").eachLine { line ->
    if(line == "inp w") {
        cmd.add([])
    } else {
        cmd[cmd.size()-1].add(line.split(" "))
    }
}

def z = 0

weight = []

for(c in cmd) {
    def alpha = c[4][2] as long
    def beta = c[3][2] as long
    def gamma = c[14][2] as long
    weight.add([alpha, beta, gamma])
    //println("-----")
}

def calc_z(w, z, weight) {
    (alpha, beta, gamma) = weight
    def x = (z%26+alpha) != w ? 1: 0
    def y = 25 * x + 1
    z = z.intdiv(beta) * y + (w+gamma) * x
}

W = new int[14]
def solve_front(i, z, mode = "min") {
    if(i>=8) {
        //println "${W[0..7]} $z"
        if(z==0) println W[0..7].collect(it->it as String).join("")
        return z==0
    }
    else {
        digits = [1,2,3,4,5,6,7,8,9]
        if( mode == "min" ) digits.reverse()
        for(w in digits) {
            W[i] = w
            if(solve_front(i+1, calc_z(W[i],z, weight[i]), mode)) return true
        }
    }
    return false 
} 

def solve_back(i, z) {
    if(i>=14) {
        //println "${W[0..7]} $z"
        if(z==0) println W[8..13].collect(it->it as String).join("")
        return z==0
    }
    else {
        for(w in 1..9) {
            W[i] = w
            if(solve_back(i+1, calc_z(W[i],z, weight[i]))) return true
        }
    }
    return false 
} 

print "first 8 digits of maximum: "
solve_front(0, 0, "max")
print "first 8 digits of maximum: "
solve_front(0, 0, "min")
print "the final digits: " 
solve_back(8, 0)


