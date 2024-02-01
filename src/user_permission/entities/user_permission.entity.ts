import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";
import { UserPost } from "../../user_post/entities/user_post.entity";

@Entity({
    name:"user_permission"
})
export class UserPermission {
        @PrimaryGeneratedColumn({
            type:"bigint"
        })
        id:number
        
        @Column()
        name_permission:string

        @OneToMany(()=>UserPost,(userPost)=>userPost.userPermission)
        postId:UserPost[]
}
