import { CreateDateColumn, Entity, ManyToOne, JoinColumn, PrimaryColumn } from "typeorm";

import { UserPermission } from "../../user_permission/entities/user_permission.entity";
import { Post } from "../../post/entities/post.entity";
import { User } from "../../users/entites/user.entity";


@Entity({
    name: "user_post"
})
export class UserPost {
    @PrimaryColumn()
    user_id: number

    @PrimaryColumn()
    post_id: number

    @ManyToOne(type => User, user => user.id)
    @JoinColumn({ name: "user_id" })
    public user!: User;

    @ManyToOne(type => Post, achievement => achievement.id)
    @JoinColumn({ name: "post_id" })
    public listPost: Post;

    @CreateDateColumn()
    created_at: Date;

    @ManyToOne(() => UserPermission, (UserPermission) => UserPermission.id)
    userPermission: UserPermission


    
}
