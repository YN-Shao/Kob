import { AcGameObjects } from "./AcGameObject";
import { Cell } from "./Cell";

export class Snack extends AcGameObjects{
    constructor(info, gamemap){
        super();
        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;

        this.cells = [new Cell(info.r , info.c)]; //Store the snake's body, and cells[0] stores the snake's head.
        this.next_cell = null;
        this.speed = 5;//The snake moves 5 squares per second
        this.direction = -1; //-1 means no command, 0, 1, 2, 3 means up, down, left and right
        this.status = "idle";//Idle means stationary, move means moving, and die means dead.
        this.dr = [-1 , 0, 1, 0];//Offset of rows in 4 directions
        this.dc = [0, 1, 0, -1];

        this.step = 0;//Number of rounds
        this.eps = 1e-2;

        this.eye_direction = 0;
        if(this.id === 1 ) this.eye_direction = 2;
        
        this.eye_dx = [
            [-1,1],
            [1,1],
            [1,-1],
            [-1,-1],
        ];
        this.eye_dy = [
            [-1,-1],
            [-1,1],
            [1,1],
            [1,-1],
        ]
    
    }

    start(){

    }

    set_direction(d){
        this.direction = d; 
    }

    check_tail_increasing(){
        if(this.step <= 10) return true;
        if(this.step %3 === 1) return true;
        return false;
    }

    next_step(){ //Change the status of the snake to take the next step
        const d = this.direction;
        this.next_cell = new Cell(this.cells[0].r + this.dr[d], this.cells[0].c + this.dc[d]);
        this.eye_direction = d;
        this.direction = -1;//Clear operation
        this.status = "move";
        this.step ++;

        const k = this.cells.length;
        for(let i=k ; i>0 ; i--){
            this.cells[i] = JSON.parse(JSON.stringify(this.cells[i-1]));
        }

    }

    updata_move(){
        //this.cells[0].x -= this.speed * this.timedelta/1000;
       
        const dx = this.next_cell.x - this.cells[0].x;
        const dy = this.next_cell.y - this.cells[0].y;
        const distance = Math.sqrt(dx * dy + dy * dy);
        if(distance < this.eps){ //Reached the target point
           this.cells[0] = this.next_cell;//Add a new snakehead
           this.next_cell = null;
            this.status = "idle";//Finished, stop

            if( !this.check_tail_increasing() ){ //If the snake does not grow longer, remove the tail
                this.cells.pop();
            }

        }
        else{
            const move_distance = this.speed * this.timedelta/1000;
            this.cells[0].x += move_distance * dx / distance;
            this.cells[0].y += move_distance * dy / distance;
            if( !this.check_tail_increasing()) {//When the snake's tail doesn't need to be longer
                const k = this.cells.length;
                const tail = this.cells[k - 1], tail_target  = this.cells[k-2];
                const tail_dx = tail_target.x - tail.x;
                const tail_dy = tail_target.y - tail.y;
                tail.x += move_distance * tail_dx / distance;
                tail.y += move_distance * tail_dy / distance;
           
            }
        }

    }

    update(){ //Execute once per frame
        if(this.status ==="move"){
            this.updata_move();
        }
         
        this.render();
    }

    render(){
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;

        ctx.fillStyle = this.color;
        if(this.status === "die"){
            ctx.fillStyle = "White";
        }

        for(const cell of this.cells){
            ctx.beginPath();
            ctx.arc(cell.x * L, cell.y * L, L/2 *0.8, 0, Math.PI*2);
            ctx.fill();
        }
        for(let i=1 ; i < this.cells.length; i++){
            const a = this.cells[i], b = this.cells[i-1];
            if( Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps)
                continue;
            if( Math.abs(a.x - b.x) < this.eps){
                ctx.fillRect((a.x - 0.4) * L, Math.min(a.y, b.y)*L,L*0.8, Math.abs(a.y - b.y) * L);
            }
            else{
                ctx.fillRect(Math.min(a.x, b.x)*L, (a.y - 0.4) * L, Math.abs(a.x - b.x)*L , L*0.8)//use square to cover round
            }
        }
        ctx.fillStyle = "black";
        for(let i=0 ; i<2 ; i++){
            const eye_x = (this.cells[0].x + this.eye_dx[this.eye_direction][i]*0.15) * L;
            const eye_y = (this.cells[0].y + this.eye_dy[this.eye_direction][i]*0.15) * L;
            ctx.beginPath();
            ctx.arc(eye_x,eye_y,L*0.08, 0, Math.PI * 2);
            ctx.fill();
        }
    }
}