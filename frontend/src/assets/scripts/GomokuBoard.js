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
        this.color = this.store.state.pk.color;


        this.rows = 15;
        this.cols = 15;
        this.pieces = [];
    }


    start(){

        this.create_pieces();
        this.add_listening_events();
    }

    set_color(color) {
        this.color = color;
    }


    create_pieces(){
        var img1 = new Image();
        for(let r = 0 ; r < this.rows ; r++){
            var pieces_row = [];
            for(let c = 0 ; c< this.cols ; c++){
                pieces_row.push(new Piece(r, c, this, "hidden"));
            }
            this.pieces.push(pieces_row);
        }
        return true;
    }

    add_piece(x, y, color) {
        this.pieces[x][y].set_color(color);
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
                    this.pieces.push(parseInt(steps[k]));
                }
                k++;
            },300);

        }else {
                this.ctx.canvas.focus();
                this.ctx.canvas.addEventListener("click", e => {
                var rect = this.ctx.canvas.getBoundingClientRect();
                let x_piece = Math.round((e.clientX - rect.left)/ (this.ctx.canvas.width / 15.2) - 0.5);
                let y_piece = Math.round((e.clientY - rect.top) / (this.ctx.canvas.height / 15.2) - 0.5);
                if( x_piece != -1 && y_piece != -1){
                    this.store.state.pk.socket.send(JSON.stringify({
                        event: "moveGomoku",
                        x: x_piece,
                        y: y_piece,
                        color: this.store.state.pk.color
                    }));
                    var rect = this.ctx.canvas.getBoundingClientRect();
                    // window.alert(JSON.stringify({
                    //     event: "moveGomoku",
                    //     x: x_piece,
                    //     y: y_piece,
                    //     color: this.store.state.pk.color
                    // }))
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
        return true;
    }
    next_step(){
    }

    // check_valid(cell){//检测撞身体、障碍物
    //     for(const wall of this.walls){//in下标，of值「
    //         if(wall.r === cell.r && wall.c===cell.c)
    //             return false;
    //     }
    //     for(const snack of this.snakes){
    //         let k = snack.cells.length;
    //         if(!snack.check_tail_increasing()){ //蛇尾前进时，蛇尾不用判断
    //             k --;
    //         }
    //         for(let i=0 ; i<k ; i++){
    //             if(snack.cells[i].r === cell.r && snack.cells[i].c === cell.c)
    //                 return false;
    //         }
    //     }
    //     return true;
    // }

    update(){
        // windows.alert(1)
        this.update_size();
        if(this.check_ready()){
            this.next_step();
        }
        this.render();
    }

    render(){
        var img1 = new Image();
        img1.src = require('@/assets/image/GomokuBoard.jpg');
        this.ctx.drawImage(img1, 0, 0, this.ctx.canvas.width, this.ctx.canvas.height);


    }
}
