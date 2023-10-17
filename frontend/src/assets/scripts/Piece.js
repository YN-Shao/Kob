import { AcGameObjects } from "./AcGameObject";

export class Piece extends AcGameObjects{
    constructor(r, c, gomokuBoard, color){
        super();

        this.r = r;
        this.c = c;

        this.gomokuBoard = gomokuBoard;
        this.color = color
    }

    start(){

    }

    update(){
        this.render();
    }

    set_color(color) {
        this.color = color
    }

    render(){
        if (this.color != "hidden") {
            const L = this.gomokuBoard.L;
            const ctx = this.gomokuBoard.ctx;
            ctx.beginPath()
            ctx.fillStyle = this.color;
          
            // ctx.fillRect(this.r*L , this.c*L,L ,L);
            ctx.arc((this.r)*L + L/2.2, this.c*L + L/2.2, L/2.2, 0, 2 * Math.PI);
            // ctx.rect((this.r - 1)*L , this.c*L,L ,L);
            ctx.fill();
        }
    }
}