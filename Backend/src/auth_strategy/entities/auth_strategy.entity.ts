import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";
import { User } from "../../users/entites/user.entity";

@Entity({
    name:"auth_strategy"
})
export class AuthStrategy {
    @PrimaryGeneratedColumn({
        type:"bigint"
    })
    id: number;

    @Column({ unique: true })
    name:string

    @OneToMany(()=>User,(user)=>user.authStrategy)
    user:User[]
}
