import { Module } from '@nestjs/common';
import { UsersController } from './controllers/users/users.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UsersService } from './service/users/users.service';
import { User } from './entites/user.entity';


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
