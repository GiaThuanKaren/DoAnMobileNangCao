import { UserPost } from "src/user_post/entities/user_post.entity";
import { Column, Entity, OneToMany, PrimaryGeneratedColumn } from "typeorm";

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

        @OneToMany(()=>UserPost,(userPost)=>userPost.post_id)
        postId:number
}
