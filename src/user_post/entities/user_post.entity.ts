import { CreateDateColumn, Entity, ManyToOne, JoinColumn, PrimaryColumn } from "typeorm";
import { User } from "src/users/entites/user.entity";
import { Post } from "src/post/entities/post.entity";
import { UserPermission } from "src/user_permission/entities/user_permission.entity";

@Entity({
    name: "user_post"
})
export class UserPost {
    @PrimaryColumn()
    user_id: string

    @PrimaryColumn()
    post_id: string

    @ManyToOne(type => User, user => user.id)
    @JoinColumn({ name: "user_id" })
    public user!: User;

    @ManyToOne(type => Post, achievement => achievement.id)
    @JoinColumn({ name: "post_id" })
    public achievement!: Post;

    @CreateDateColumn()
    created_at: Date;

    @ManyToOne(() => UserPermission, (UserPermission) => UserPermission.id)
    userPermission: UserPermission

}
