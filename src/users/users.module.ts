import { Module } from '@nestjs/common';
import { UsersController } from './controllers/users/users.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { User } from 'src/typeorm/entites/User';
import { UsersService } from './service/users/users.service';


@Module({
 

  controllers: [UsersController],
 
  imports:[
    TypeOrmModule.forFeature([
      User
    ])
  ],
  providers: [ UsersService]
})
export class UsersModule {}
