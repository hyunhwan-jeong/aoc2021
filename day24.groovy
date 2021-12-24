cmd = []
new File("day24.in").eachLine { line ->
    cmd.add(line.split(" "))
}

def solve(test) {
    value = ["x": 0G, "y": 0G, "z": 0G, "w": 0G]
    def j = 0
    for(c in cmd) {
        if(c[0] == "inp") {
            //println "========================================"
            value["w"] = test[j++] as BigInteger
            //println "$c: $value"
            continue
        } 

        b = 0G
        if(c[2] != "x" && c[2] != "y" && c[2] != "z" && c[2] != "w") {
            b = c[2] as BigInteger
        } else {
            b = value[c[2]]
        }

        if(c[0] == "add") value[c[1]] = value[c[1]].add(b)
        else if(c[0] == "mul") value[c[1]] = value[c[1]].multiply(b)
        else if(c[0] == "div") value[c[1]] = value[c[1]].divide(b)
        else if(c[0] == "mod") value[c[1]] = value[c[1]].mod(b)
        else if(c[0] == "equ") {
            if(value[c[1]] == b) value[c[1]] = 1G
            else value[c[1]] = 0G
        }
        // println "$c: $value"
        //println "$c: $value"
    }
    println "$value"
}

solve([1,1,1,1,1,1,1,1,1,1,1,1,1,1])
solve([2,2,2,2,2,2,2,2,2,2,2,2,2,2])
solve([3,3,3,3,3,3,3,3,3,3,3,3,3,3])
solve([4,4,4,4,4,4,4,4,4,4,4,4,4,4])
solve([9,9,9,9,9,9,9,9,9,9,9,9,9,9])
solve([1,3,5,7,9,2,4,6,8,9,9,9,9,9])
