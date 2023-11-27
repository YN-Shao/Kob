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

            const steps = this.store.state.record.gomoku_steps.split(" ");
            const loser = this.store.state.record.record_loser;
            // const pieces = this.pieces;
            const interval_id = setInterval(() => {
                if( k >= steps.length - 1){
                    clearInterval(interval_id);
                }else{
                    this.pieces[parseInt(steps[k].split(",")[0])][parseInt(steps[k].split(",")[1])].set_color(steps[k].split(",")[2]);
                }
                // window.alert(steps[k])
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
