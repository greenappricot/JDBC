SELECT * FROM BS.EMPLOYEE;
UPDATE BS.EMPLOYEE SET SALARY=1000000;
ROLLBACK;

CREATE TABLE TEST(
    TEST_NO NUMBER,
    TESTCONTENT VARCHAR2(200)
);

-- TCL : Ʈ������� ��Ʈ���ϴ� ��ɾ�
-- COMMIT : ���ݱ��� ������ ��������(DML)��ɾ ��� DB�� ����
-- ROLLBACK : ���ݱ��� ������ ��������(DML)��ɾ ��� ���
-- Ʈ����� : �ϳ��� �۾����� �Ѱ� ���� -> COMMIT�ؾ� �Ѵ�
-- Ʈ������� ����� �Ǵ� ��ɾ�� : DML(INSERT,UPDATE,DELETE)

INSERT INTO JOB VALUES('J0','����');
SELECT * FROM JOB;
COMMIT; 

-- ORACLE���� �����ϴ� OBJECTȰ���ϱ�
-- USER, TABLE, VIEW, SEQUENCE, INDEX, SYNONYM, FUNTION, PROCEDURE, PACKAGE ��
