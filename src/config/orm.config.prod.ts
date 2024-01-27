import { registerAs } from '@nestjs/config';
import { TypeOrmModuleOptions } from '@nestjs/typeorm';
import { Post } from 'src/post/entities/post.entity';
import { User } from 'src/users/entites/user.entity';
// import { Goal } from 'src/goals/entities/goal.entity';

export default registerAs(
  'orm.config',
  (): TypeOrmModuleOptions => ({
    type: 'postgres',
    host: 'aws-0-ap-southeast-1.pooler.supabase.com',
    port: 6543,
    username: 'postgres.phzphepbggestnawyqbk',
    password: 'giathuanAndroid',
    database: "postgres",
    entities:[
       Post,
      User
    ],
    synchronize:true,
    autoLoadEntities:true,
    
  }),
);