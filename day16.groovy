new File("day16.in").eachLine {
    solve(it)
}

def to_dec(A) {
    ret = 0
    for(x in A) ret = ret * 2 + x
    ret 
}

def get_version(C) {
    def version = to_dec(C[0..2])
    def packet_type = to_dec(C[3..5])
    def bin_str = C[0..C.size()-1].collect(i->i as String).join("")
    //println "version: $version, type: $packet_type, with $bin_str"
    score += version

    def i = 6
    def rest = []
    if(packet_type == 4) {
        number = 0
        do {
            is_ended = C[i] == 0
            number = number * 16 + to_dec(C[i+1..i+4])
            i += 5
        } while(!is_ended)
        // while(i%4!=0) i++
        if(i <= C.size()-1) {
            rest = C[i..C.size()-1]
        } else {
            rest = []
        }
        //println "rest ${rest.collect(a->a as String).join('')}"
    } else {
        is_length_given = C[i++] == 0
        if(is_length_given) {
            def len = to_dec(C[i..i+14])
            //println "len is $len"
            i += 15
            
            rest = C[i..i+len-1]
            while(rest.size()>0) {
                rest = get_version(rest)
                //println "rest after mode 0 - ${rest.collect(a->a as String).join('')}"
            }

            if( i + len == C.size() ) {
                rest = []
            } else {
                rest = C[i+len..C.size()-1]
            }
            
        } else {
            sz = to_dec(C[i..i+10])
            //println "sz is $sz"
            i += 11
            rest = C[i..C.size()-1]
            //println "rest is ${rest.join("")}"
            for(k in 0..sz-1) {
                //println "k is $k"
                rest = get_version(rest)
            }
        }
    }
    rest
 }
def solve(input) {
    B = []    
    for(c in input.toCharArray()) {
        d = 0
        if(c >= '0' && c <= '9') {
            d = c as int - ('0' as char as int)
        } else {
            d = c as int - ('A' as char as int) + 10
        }
        B.add(d.intdiv(8))
        B.add(d.intdiv(4)%2)
        B.add(d.intdiv(2)%2)
        B.add(d%2)
    }

   
    score = 0
    println input
    get_version(B)
    println score
}