import { User } from "src/users/entites/user.entity";
import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";

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
