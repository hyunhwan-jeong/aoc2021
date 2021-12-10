match = [ ")": "(", "]": "[", ">": "<", "}" : "{" ]
open = [ "(", "[", "<", "{" ] as Set
score = [ ")" : 3, "]" : 57, "}": 1197, ">": 25137 ] 

ret = 0
new File("day10.in").eachLine { line ->
    line = [line.toCharArray()].flatten()
    stk = [] as Stack
    for(c in line) {
        c = c as String
        if(open.contains(c)) {
            stk.push(c as String)
        } else {
            if(stk.isEmpty()) {
                break
            }
            top = stk.pop()
            if(match[c] != top) {
                ret += score[c]
                break         
            }
        }
    }
}

println ret