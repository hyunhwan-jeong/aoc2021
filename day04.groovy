class Bingo {
    private row, col, diag, anti
    private remains
    private board
    private score

    public Bingo(lines) {
        this.diag = 0
        this.anti = 0
        this.row = new int[5]
        this.col = new int[5]
        this.score = 0
        this.remains = 0
        this.board = new int[5][5]
        for (int i = 0; i < lines.size(); i++) {
            String[] numbers = lines[i].stripIndent().stripMargin().split("\s+")
            for (int j = 0; j < numbers.size(); j++) {
                this.board[i][j] = numbers[j] as int
                this.remains += board[i][j]
            }
        }
    }   

    public int check(int num) {
        if(this.score > 0){
            return 0
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.board[i][j] == num) {
                    this.board[i][j] = -1
                    this.remains -= num

                    if(++this.row[i] == 5) {
                        //println "$this.remains $num"
                        this.score = this.remains * num
                    }
                    if(++this.col[j] == 5) {
                        //println "$this.remains $num"
                        this.score = this.remains * num
                    }
                    /*if (i == j) {
                        if(++this.diag == 5) {
                            println "$this.remains $num"

                            return this.remains * num
                        }
                    }
                    if (i + j == 4) {
                        if(++this.anti == 5) {
                            println "$this.remains $num"

                            return this.remains * num
                        }
                    }*/
                }
            }
        }
        return this.score
    }

    public void show() {
        for(i in 0..4) {
            for(j in 0..4) {
                print sprintf("%3d", this.board[i][j])
            }
            println ""
        }
        println ""

    }
}

new File("day04.in").withReader { reader ->
    numbers = reader.readLine().split(",").collect{ it as int }
    players = []

    println numbers
    while( reader.readLine() != null ) {
        lines = []
        for(i in 0..4) {
            lines.add(reader.readLine())
        }
        players.add(new Bingo(lines))
    }

    println players.size()
    for(num in numbers) {
        for(player in players) {
            ret = player.check(num)
            // player.show()
            if(ret > 0) {
                println ret
            }
        }   
    }
}

