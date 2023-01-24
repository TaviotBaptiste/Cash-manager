import { Table, Column, DataType, Model, HasMany } from 'sequelize-typescript'
  
@Table
export class Users extends Model {
  
  @Column({
    allowNull: false,
    unique: true,
    type: DataType.TEXT,
  })
  email: string;
  
  @Column({
    allowNull: false,
    unique: false,
    type: DataType.TEXT,
  })
  firstname: string;
  
  @Column({
    allowNull: false,
    unique: false,
    type: DataType.TEXT,
  })
  lastname: string;
  
  @Column({
    allowNull: false,
    unique: false,
    type: DataType.TEXT,
  })
  password: string;

  @Column({
    allowNull: false,
    unique: false,
    type: DataType.FLOAT,
  })
  solde: number;
  
  @Column({
    allowNull: false,
    unique: false,
    type: DataType.BOOLEAN,
  })
  admin: boolean;
  
} 