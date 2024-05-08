import { Column, Entity, ManyToMany, ManyToOne, PrimaryColumn, PrimaryGeneratedColumn } from "typeorm";
import { User } from "../../users/entites/user.entity";

@Entity(
    {
        name:"Notification"
    }
)
export class Notification {
    @PrimaryColumn({
        
    })
    id: string;

    @Column()
    token:string 


    @ManyToOne(()=>User,(user)=>user.listToken)
    listUser:User

}
