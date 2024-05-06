import { Module } from '@nestjs/common';
import { NotificationService } from './notification.service';
import { NotificationController } from './notification.controller';
import { messagingProvider } from './MessageProvider';
import { MessagingService } from './MessagingService';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Notification } from './entities/notification.entity';
import { User } from '../users/entites/user.entity';

@Module({
  controllers: [NotificationController],
  providers: [NotificationService],
  imports: [
    TypeOrmModule.forFeature([
      Notification,
      User
    ])
  ]
  // exports: [MessagingService]
})
export class NotificationModule { }
