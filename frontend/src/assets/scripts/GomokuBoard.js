/* eslint-disable */
import { AcGameObjects } from "./AcGameObject";
import { Piece } from "./Piece";

export class GomokuBoard extends AcGameObjects{
    constructor(ctx, parent, store){
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.store = store;
        this.L = 0;


        this.rows = 19;
        this.cols = 19;
        this.pieces = [];
    }


    start(){

        this.create_walls();
        this.add_listening_events();
    }

    create_walls(){
        const g = this.store.state.pk.gomokuBoard;
        for(let r = 0 ; r < this.rows ; r++){
            for(let c = 0 ; c< this.cols ; c++){
                if(g[r][c]) {
                    this.piece.push(new piece(r, c, this, "#B37226"));
                }
            }
        }
        return true;
    }

    add_listening_events(){
    
        if(this.store.state.record.is_record){
            let k = 0;

            console.log(this.store.state.record);

            const steps = this.store.state.record.steps;
            const loser = this.store.state.record.record_loser;
            // const pieces = this.pieces;
            const interval_id = setInterval(() => {//每300ms传入一次方向
                if( k >= a_steps.length - 1){
                    clearInterval(interval_id);
                }else{
                    this.pieces.push(parseInt(steps[k]))
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
        for(const snake of this.snakes){  //判断两条蛇是否准备好下一回合
            if(snake.status !== "idle") return false;
            if(snake.direction === -1) return false;
        }
        return true;
    }
    next_step(){//让两条蛇进入下一回合
        for(const snake of this.snakes){
            snake.next_step();
        }
    }

    check_valid(cell){//检测撞身体、障碍物
        for(const wall of this.walls){//in下标，of值「
            if(wall.r === cell.r && wall.c===cell.c)
                return false;
        }
        for(const snack of this.snakes){
            let k = snack.cells.length;
            if(!snack.check_tail_increasing()){ //蛇尾前进时，蛇尾不用判断
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