import { AcGameObjects } from "./AcGameObject";

export class Piece extends AcGameObjects{
    constructor(r, c, gomokuBoard, color){
        super();

        this.r = r;
        this.c = c;
        this.gomokuBoard = gomokuBoard;
        this.color = color
 
    }
    update(){
        this.render();
    }

    render(){
        const L = this.gomokuBoard.L;
        const ctx = this.gomokuBoard.ctx;

        ctx.fillStyle = this.color;
      
        ctx.fillRect(this.c*L , this.r*L,L ,L);
    }
}