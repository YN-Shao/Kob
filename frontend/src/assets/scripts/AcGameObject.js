const AC_GAME_OBJECTS = [];
export class AcGameObjects{
    constructor(){
        AC_GAME_OBJECTS.push(this);
         this.timedelta = 0;
         this.has_called_start = false;
    }

    start(){

    }

    update(){//除了第一次，以后每次都执行

    }
    on_destroy(){ //删除之前

    }

    destroyed() {
        this.on_destroy();

        for(let i in AC_GAME_OBJECTS){
            const obj = AC_GAME_OBJECTS[i];
            if(obj == this){
              AC_GAME_OBJECTS.splice(i);
              break;
            }
         }
    }   

}


let last_timestamp;
const step = timestamp => {
    for(let obj of AC_GAME_OBJECTS){
        if( !obj.has_called_start){
            obj.has_called_start = true;
            obj.start();
        }
        else{
            obj.timedelta = timestamp - last_timestamp;
            obj.update()
        }
    }
    
    last_timestamp = timestamp ;
    requestAnimationFrame(step)
}

requestAnimationFrame(step)