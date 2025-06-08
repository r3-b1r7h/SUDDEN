package biz.sudden.knowledgeData.kdm.repository.hibernate;

import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.kdm.domain.FileContainer;
import biz.sudden.knowledgeData.kdm.repository.FileContainerRepository;

public class FileContainerRepositoryImpl extends
		GenericRepositoryImpl<FileContainer, Long> implements
		FileContainerRepository {

	public FileContainerRepositoryImpl() {
		// TODO Auto-generated constructor stub
		super(FileContainer.class);
	}

	@Override
	public Long create(final FileContainer o) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(final Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub

				String statement = "INSERT INTO FILECONTAINER (file) VALUES (?)";
				PreparedStatement stmt = session.connection().prepareStatement(
						statement);
				try {
					stmt.setBinaryStream(1, new FileInputStream(o.getFile()),
							(int) o.getFile().length());
					stmt.executeUpdate();
				} catch (Exception ex) {
					return new Long(0);
				}
				return new Long(1);
			}

		});

	}
}
