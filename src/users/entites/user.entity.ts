import { AuthStrategy } from 'src/auth_strategy/entities/auth_strategy.entity';
import {
    Column,
    Entity,
    JoinColumn,
    ManyToOne,
    OneToMany,
    OneToOne,
    PrimaryGeneratedColumn,
  } from 'typeorm';
//   import { Post } from './Post';
//   import { Profile } from './Profile';
  
  @Entity({ name: 'users' })
  export class User {
    @PrimaryGeneratedColumn({
        type:"bigint"
    })
    id: number;
  
    @Column({ unique: true })
    username: string;
  
    @Column() 
    password: string;
  
    @Column()
    createdAt: Date;
  

  
    @ManyToOne(()=>AuthStrategy,(AuthStrategy)=>AuthStrategy.user)
    authStrategy:AuthStrategy
    // @OneToOne(() => Profile)
    // @JoinColumn()
    // profile: Profile;
  
    // @OneToMany(() => Post, (post) => post.user)
    // posts: Post[];
  }
  