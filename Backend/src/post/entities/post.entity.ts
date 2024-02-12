import { Column, Entity, JoinColumn, ManyToOne, OneToMany, PrimaryGeneratedColumn } from "typeorm";

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

    
    @Column({ nullable: true })
    parentId: number;

    @ManyToOne(() => Post, post => post.id)
    @JoinColumn({ name: 'parentId' })
    parent: Post;

    // @OneToMany(() => Post, post => post.parent)
    // replies: Post[];
  
}
 