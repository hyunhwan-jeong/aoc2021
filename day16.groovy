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
    score += version

    def i = 6
    def rest = []
    if(packet_type == 4) {
        number = 0G
        do {
            is_ended = C[i] == 0
            number = number * 16 + to_dec(C[i+1..i+4])
            i += 5
        } while(!is_ended)

        if(i <= C.size()-1) {
            rest = C[i..C.size()-1]
        } else {
            rest = []
        }
        println "return $number, $rest"
        return [number, rest]

    } else {
        is_length_given = C[i++] == 0

        def values = []
        if(is_length_given) {
            def len = to_dec(C[i..i+14])
            i += 15
            
            rest = C[i..i+len-1]
            while(rest.size()>0) {
                (ret_num, rest) = get_version(rest)
                values.add(ret_num)
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
            for(k in 0..sz-1) {
                (ret_num, rest) = get_version(rest)
                values.add(ret_num as BigInteger)
            }
        }
        println "$packet_type, $values"
        ret_val = 0G
        if(packet_type == 0) {
            
            ret_val = values.sum()
        }
        else if(packet_type == 1) {
            mul = 1G
            for(c in values) mul *= c as BigInteger
            ret_val = mul
        }
        else if(packet_type == 2) ret_val = values.min()
        else if(packet_type == 3) ret_val = values.max()
        else if(packet_type == 5) ret_val = values[0] > values[1] ? 1G: 0G
        else if(packet_type == 6) ret_val =  values[0] < values[1] ? 1G: 0G
        else if(packet_type == 7) ret_val = values[0] == values[1] ? 1G: 0G

        return [ret_val as BigInteger, rest]
    }
    return [0 as BigInteger, []]
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
    println get_version(B)[0]
}