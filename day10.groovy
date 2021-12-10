match = [ ")": "(", "]": "[", ">": "<", "}" : "{" ]
open = [ "(", "[", "<", "{" ] as Set
score = [ ")" : 3, "]" : 57, "}": 1197, ">": 25137 ] 
score2 = [ "<" : 4, "{": 3, "[": 2, "(": 1]
ret_task1 = 0
ret_task2 = []
new File("day10.in").eachLine { line ->
    line = [line.toCharArray()].flatten()
    stk = [] as Stack

    corrupted = false
    for(c in line) {
        c = c as String
        if(open.contains(c)) {
            stk.push(c as String)
        } else {
            if(stk.isEmpty()) {
                corrupted = true
                break
            }
            top = stk.pop()
            if(match[c] != top) {
                ret_task1 += score[c]
                corrupted = true
                break         
            }
        }
    }

    if(!corrupted && !stk.isEmpty()) {
        cur_score2 = 0 as BigInteger
        while(!stk.empty()) {
            cur_score2 *= 5 as BigInteger
            cur_score2 += score2[stk.pop()] as BigInteger
        }
        ret_task2.add(cur_score2)
    }
    
}
ret_task2.sort()

println ret_task1
println ret_task2.size()
println ret_task2
println ret_task2[ret_task2.size().intdiv(2)]