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
    {
      privateKey: "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDMIcmNVDWN/hye\nItpl5tZ2FUX4VIcmfhQjYcKFdEWTp9K+fY6pdNAEx6I/p+9wBQl9W4ivF8eYjn58\nExbVRdKz9E090V+o5T5M+TZdq/65FodR2cYWJrr38s756mNKUaTN2sRNS2p43+1i\nm7GHSdaqur0u/OtRSebqFH90nS20x/f9Dcji7VkfUb5WQbjcHqkS1fXSdASWCJHZ\nQrj790C6S8DCd6VLiTKehu8gO7o4mEFGwRlvQZJGnm9IY1u700Yt1OmFBxBaJIEe\nO09BvBmgsK2A4YrZr2hb6tNEBvmtdUswWTMTZ0SQACgBG6idodN6svwQ6NmIJXqV\nrxZlMY33AgMBAAECggEADId0XgLRqK30prYf7oo9FpFWspfJldC+Pq+050kMLjRq\nSHvfYkJDtE9mg360wQzmGLYiDREqFJCGbeokCCIZfScdCJLNysmLSeEv947jewvJ\ncXoq4NguuKJEFu3Oe1TIh0QKsW3UEqg340oIGvQqw17j1rr6XIprLGnmqsss74AM\nY1P/Ri9+0wczpt+tYbzuCXAQ+4UFHuOTyq2F5IsoBCHE/TXttA2DcSDR1Xk056EF\njrKlkJt5x9ICtB1KjmV3ZPB1Y290HMojXUULLostf2jkXy4G/lZfDNKEJK7WV+Sk\naqHWubHlerFJxcqb2/5tGy4AUJ2zAevfbd8kGIAbwQKBgQD8Pc9JXoRV6l8jgdhg\nDHX2k1R1b0jIqm7K2dkIVParBVgG7isV1zsBb37QD86f5H60wAftUtTnSup30SE4\n3D3sFFtBXBtEAnFnwLxNU8d3UZavTfY3MiferkZ47hC9aX8MHc1RLTQDRBLZnzHg\nx8cQNpNEFPNAamDhUA91ZoeEQQKBgQDPLHYNE8dMK7/vLrLWUZB2x0mY2i3l6fcY\nyxQ/A+qf3wh3KmFQKdzAJYAnEHy8FcyHvTg/qi86im76KaWxe7Ihp7FSoblDbuGU\nbERVKu7/wwArD9vh70tfFubUQt7YZYCNdkHYEHuKkyAYP/e9jPddDOA0TSppwkpJ\nzKK3tLskNwKBgQDHU9zTYNJ4xk3KVFh1hkNNyB0s8/TzReNpf39HW0TV3tIyH9GC\nX+Pr/Xyb1HH23mzWTw20mAlQgV4JswRq13hDb86xNqUBOGD/mUn0XWk5vAq0m4of\nEg55pQRhmdc2pwBTxdUy+eKpVgkDWg7jT1vaTxZO7bNQSwAl7MtafdxHQQKBgHVi\nPr0oILsi6sxNzFysuErknemybWEg0LXGkwElafINgBFs/Nhb780bMmJv8Azt2QZs\nCwvflIT53Ol3YX+Pk52IE2u7YGo50Cy1r34GJobZ6SV9BdwdJrqJEl7gSL2ZvV3p\nzGLs6+LXe87hp7BJx/p8Cij4M/jachcgkG3ARGy/AoGAA1w4XfJo8shWjn9QK6nL\nGgUAlzAioZgitv3cW8wYikKAsU/zl3qrI7PlIgMiCx6QTqp4Bsx7OA3xOGNtJIkY\n0qpwAiNx+GGMZ/OZNFdePxPlz+7sSym/Xe8r9LR94SUGM6GPMpfXxbcHY1Ihj3jf\nz+gj6YcVPLeZxVJOlW1qpLg=\n-----END PRIVATE KEY-----\n",
      clientEmail: "firebase-adminsdk-9hrej@standardblognote-a845b.iam.gserviceaccount.com",
      projectId: "standardblognote-a845b"
    }
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
    idUser: string,
    token: string,
    idDevice: string
  ) {
    try {

      let result = await this.notifycationRepository.upsert(
        {
          id: idDevice,
          listUser: {
            id: idUser
          },
          token: token,

        },
        {
          conflictPaths: {
            id: true
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

    const foundUserToken = await this.notifycationRepository.findOne({
      where:{
        listUser:{
          id:userId
        }
      }
    })
    console.log(foundUserToken)
    const message = {
      notification: {
        title: "Gia Thuan ",
        body:messsage,
      },
      token:
      foundUserToken.token,
    };

    try {
      await firebase.messaging().send(message);
      return foundUserToken
    } catch (e) {
      console.log(e)
      throw e
    }
  }
}
