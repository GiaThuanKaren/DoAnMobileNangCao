import { registerAs } from '@nestjs/config';
import { TypeOrmModuleOptions } from '@nestjs/typeorm';
import { Post } from 'src/post/entities/post.entity';
import { User } from 'src/users/entites/user.entity';

export default registerAs(
  'orm.config',
  (): TypeOrmModuleOptions => ({
    type: 'mysql',
    host: 'mysql-giathuan-giathuannguyen213-5e78.a.aivencloud.com',
    port: 18481,
    username: 'avnadmin',
    password: 'AVNS_6EEaBTyBsp2oVSODdA2',
    database: "defaultdb",
    entities:[
      Post,
      User
    ],
    synchronize:true,
    autoLoadEntities:true,
    driver:{
      
    }
    
  }),
);