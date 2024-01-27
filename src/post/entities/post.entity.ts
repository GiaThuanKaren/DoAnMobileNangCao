import { Column, Entity, JoinColumn, OneToMany, PrimaryGeneratedColumn } from "typeorm";

@Entity({ name: 'posts' })
export class Post {
    @PrimaryGeneratedColumn({
        type:"bigint"
    })
    id: number;
  
    @Column()
    title: string;
  
    @Column()
    description: string;

    @Column()
    icon:string

    @Column()
    coverImagelink:string

    @OneToMany(() => Post, Post => Post.id, { nullable: true })
    @Column()
    // @JoinColumn({
    //     name:"id"
    // })
    parentPost:number
}
