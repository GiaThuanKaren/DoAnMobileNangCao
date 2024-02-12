import { Module } from '@nestjs/common';
import { UserPermissionService } from './user_permission.service';
import { UserPermissionController } from './user_permission.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UserPermission } from './entities/user_permission.entity';

@Module({
  controllers: [UserPermissionController],
  providers: [UserPermissionService],
  imports: [
    TypeOrmModule.forFeature([
      UserPermission
    ])
  ]
})
export class UserPermissionModule { }
