import { AcGameObjects } from "./AcGameObject";
import { Snack } from "./Snake";
import { Wall } from "./Wall";

export class GameMap extends AcGameObjects{
    constructor(ctx, parent, store){
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.store = store;
        this.L = 0;


        this.rows = 13;
        this.cols = 14;

        this.inner_walls_counts = 40;
        this.walls = [];

        this.snakes = [
              new Snack({id:0, color:"#4876EC", r:this.rows-2, c:1}, this),
              new Snack({id:1,color:"#F94848", r:1 , c:this.cols-2},this)
        ]

    }


    start(){

        this.create_walls();
        this.add_listening_events();
    }

    create_walls(){
        const g = this.store.state.pk.gamemap;
        for(let r = 0 ; r < this.rows ; r++){
            for(let c = 0 ; c< this.cols ; c++){
                if(g[r][c]) {
                    this.walls.push(new Wall(r, c ,this));
                }
            }
        }
        return true;
    }

    add_listening_events(){
    
        if(this.store.state.record.is_record){
            let k = 0;

            console.log(this.store.state.record);

            const a_steps = this.store.state.record.a_steps;
            const b_steps = this.store.state.record.b_steps;
            const loser = this.store.state.record.record_loser;
            const [snake0, snake1] = this.snakes;
            const interval_id = setInterval(() => {//Directions are passed in every 300ms
                if( k >= a_steps.length - 1){
                    if(loser === "all" || loser ==="A"){
                        snake0.status = "die";
                    }
                    if(loser === "all" || loser ==="B" ){
                        snake1.status = "die";
                    }
                    clearInterval(interval_id);
                }else{
                    snake0.set_direction(parseInt(a_steps[k]));
                    snake1.set_direction(parseInt(b_steps[k]));
                }
                k++;
            },300);

        }else {
                this.ctx.canvas.focus();
                this.ctx.canvas.addEventListener("keydown", e => {
                let d = -1;
                if(e.key === 'w') d=0;
                else if(e.key ==='d') d=1;
                else if(e.key ==='s') d=2;
                else if(e.key ==='a') d=3;
                if( d>=0 ){
                    this.store.state.pk.socket.send(JSON.stringify({
                        event: "move",
                        direction: d,
                    }));
                }
            });
        }
    }

    update_size(){
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows))
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }   

    check_ready(){
        for(const snake of this.snakes){  //Determine whether two snakes are ready for the next turn
            if(snake.status !== "idle") return false;
            if(snake.direction === -1) return false;
        }
        return true;
    }
    next_step(){//Let both snakes advance to the next round
        for(const snake of this.snakes){
            snake.next_step();
        }
    }

    check_valid(cell){//Detect body collisions and obstacles
        for(const wall of this.walls){
            if(wall.r === cell.r && wall.c===cell.c)
                return false;
        }
        for(const snack of this.snakes){
            let k = snack.cells.length;
            if(!snack.check_tail_increasing()){ //When the snake's tail moves forward, the snake's tail does not need to judge
                k --;
            }
            for(let i=0 ; i<k ; i++){
                if(snack.cells[i].r === cell.r && snack.cells[i].c === cell.c)
                    return false;
            }
        }
        return true;
    }

    update(){
        this.update_size();
        if(this.check_ready()){
            this.next_step();
        }
        this.render();
    }

    render(){
       /* this.ctx.fillStyle = 'green';
        this.ctx.fillRect(0,0, this.ctx.canvas.width, this.ctx.canvas.height);
        */
        const color_even = "#AAD751", color_odd = "#A2D149";
        for(let r = 0 ; r < this.rows ; r++ ){
            for(let c = 0 ; c < this.cols ; c++ ){
                if( (r+c) % 2 == 0 ){
                    this.ctx.fillStyle = color_even;
                }
                else{
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
    }
}