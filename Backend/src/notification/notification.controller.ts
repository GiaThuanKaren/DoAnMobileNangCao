import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { NotificationService } from './notification.service';
import { CreateNotificationDto } from './dto/create-notification.dto';
import { UpdateNotificationDto } from './dto/update-notification.dto';
import { ApiTags } from '@nestjs/swagger';
import { UpdateTokenDTO } from './dto/updateToken.dto';
import { message_notifyDTO } from './dto/message_notify.dto';

@Controller('notification')
@ApiTags("notification")
export class NotificationController {
  constructor(private readonly notificationService: NotificationService) {}
  @Post("sendnotify")
  async Sentest(
   @Body() messageDto:message_notifyDTO
  ){
    await this.notificationService.SendMessage(messageDto.userid,messageDto.message)

  }


  @Post()
  updateTokenByidUser(
    @Body()  updateToken:UpdateTokenDTO
  ){
    return this.notificationService.UpdateTokenByIdUser(updateToken.idUser,updateToken.token,
      updateToken.idDevice)
  }

  // @Post()
  // create(@Body() createNotificationDto: CreateNotificationDto) {
  //   return this.notificationService.create(createNotificationDto);
  // }

  // @Get()
  // findAll() {
  //   return this.notificationService.findAll();
  // }

  // @Get(':id')
  // findOne(@Param('id') id: string) {
  //   return this.notificationService.findOne(+id);
  // }

  // @Patch(':id')
  // update(@Param('id') id: string, @Body() updateNotificationDto: UpdateNotificationDto) {
  //   return this.notificationService.update(+id, updateNotificationDto);
  // }

  // @Delete(':id')
  // remove(@Param('id') id: string) {
  //   return this.notificationService.remove(+id);
  // }





}
