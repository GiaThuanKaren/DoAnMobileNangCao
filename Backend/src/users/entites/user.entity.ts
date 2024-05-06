import {
    Column,
    CreateDateColumn,
    Entity,
    JoinColumn,
    ManyToOne,
    OneToMany,
    OneToOne,
    PrimaryGeneratedColumn,
    UpdateDateColumn,
  } from 'typeorm';
import { AuthStrategy } from '../../auth_strategy/entities/auth_strategy.entity';
import { Notification } from '../../notification/entities/notification.entity';
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
    // @Exclude()
    password: string;
  
    // @Column({ unique: true })
    // email:string

    @CreateDateColumn()
    createdAt: Date;
    
    @UpdateDateColumn()
    updateAt:Date;
  
    @ManyToOne(()=>AuthStrategy,(AuthStrategy)=>AuthStrategy.user)
    authStrategy:AuthStrategy


    @OneToMany(()=>Notification,(notification)=>notification.listUser)
    listToken:Notification[]
    // @OneToOne(() => Profile)
    // @JoinColumn()
    // profile: Profile;
  
    // @OneToMany(() => Post, (post) => post.user)
    // posts: Post[];


  }
  