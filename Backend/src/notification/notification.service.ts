import { MSG } from './../utils/index';
import { Injectable } from '@nestjs/common';
import { CreateNotificationDto } from './dto/create-notification.dto';
import { UpdateNotificationDto } from './dto/update-notification.dto';
import * as firebase from 'firebase-admin';
import { Notification } from './entities/notification.entity';
import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
import { User } from '../users/entites/user.entity';

// import ServiceAccount  from "../serviceAccountKey.json"
firebase.initializeApp({
  credential: firebase.credential.cert(
    "./serviceAccountKey.json"
  ),
});


@Injectable()
export class NotificationService {

  constructor(
    @InjectRepository(Notification) private notifycationRepository: Repository<Notification>,
    @InjectRepository(User) private userRepository: Repository<User>,
  ) {

  }

  create(createNotificationDto: CreateNotificationDto) {
    return 'This action adds a new notification';
  }


  findAll() {
    return `This action returns all notification`;
  }

  findOne(id: number) {
    return `This action returns a #${id} notification`;
  }

  update(id: number, updateNotificationDto: UpdateNotificationDto) {
    return `This action updates a #${id} notification`;
  }

  remove(id: number) {
    return `This action removes a #${id} notification`;
  }



  async UpdateTokenByIdUser(
    idUser: number,
    token: string,
    idDevice: string
  ) {
    try {

      let result =await this.notifycationRepository.upsert(
        {
          id: idDevice,
          listUser: {
            id: idUser
          },
          token: token,

        },
        {
          conflictPaths: {
            id:true
          }
        }
      )
      return MSG(
        "Done",
        result
      )

    } catch (error) {
      return error
    }
  }


  async SendMessage(
    userId: string,
    messsage: string
  ) {


    const message = {
      notification: {
        title: "Gia THuan ",
        body: "You have a new message from ChatGPT",
      },
      token:
        "dGY26n65RFGc-hPHbaVQP7:APA91bH8Dlee5lD3Z1zhq11aqZDhGBIgKEQEuOQDu5bE_mRiBHAPOF_dhB8s4VDhUhzHM3DQ1L8S2N-0bDvIYBKDw_qlW93QZRSPQOkHD-J9zElSLN3j9ehxFvdZ1-xGQQUSsKbLagPjl",
    };

    try {
      await firebase.messaging().send(message);
    } catch (e) {
      throw e
    }
  }
}
